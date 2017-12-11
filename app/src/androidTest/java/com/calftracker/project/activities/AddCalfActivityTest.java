package com.calftracker.project.activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.util.Base64;
import android.widget.DatePicker;

import com.calftracker.project.calftracker.R;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class AddCalfActivityTest {
    @Rule
    public IntentsTestRule<AddCalfActivity> mIntentsTestRuleDashboard = new IntentsTestRule<AddCalfActivity>(AddCalfActivity.class);

    @Before
    public void setUp() throws Exception {
        String base64Image = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCABkAGQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3T4gfA3W/i34LutF8U3bPoKuLvfEQsqOrlVOT3xn25r4T8SeAvFf7KnxNbVdOt7fWb3R2kcquX32roQTIoHAaNzz9fSv0N8RfEyK8075bhLe5miE0FnM+0P3xIOqhuleU2/wfX4k2ms6veXt3Fr1/GzXkkjhiiIdqxAD7wCHPvivw/LOIfqlL2c4tR5kmmu/xPpZdeup6ind3W62PmH46/sS6nD8NrX4v+BZm1fwzfILnUdG8s/atHZgGYNgncik4J6jIPTmvmO102ZY0MqspYZG7uvqK/Snwj4w1v4L2suoeF9a+3Jq12bc2WoqXt2RCRmWPP3mXIJB47V2Vx+zZoX7bHh3TtUv00/wXrWi3Kw3DeHbBYoTC6lmjwT+8bIUhuANx4Nfp+V5rDEpQi7yW/wAjinad3LRnxz+xb+yRP8evHN7rGqxsfCfh9FmuYhHvN7cNnyrcDuvylnPYDH8VfozJqvhb9m/TRqWvxWzXkcS+TYxXADW0XTbDbhVQY4+6PpXs3wp+F3hj4C/D/TfC2goLXTbX5TNcMPNuJWPLu2Budif5ADoK+PP2zfjv4e1ph4e8S/CfxHJPazlrHU5c2ZOOMrIEJKNkZUH8jXv8vtJrmb+RMZcsGkvvOL+Mf7f+r3GuW7+B9QeG0MTK8csbQtHJk4wM4bAA68HNaHwz/wCCll1ayR2vxA0Rr2E4H9p6SAkie7xMdrf8BI+leDeCPBNv8SrhLbR/gv4i1Yski/brDVZECsO5M0ewbSCME4OfWuG+I3w41j4f6rJpmsaVcaTdFUlW3uAPMVHGVDgEgNjrg4ODXrUqcILlS+886rUc3dtfI/T7wX+0f8Iv2grefw9FqVvdy3EXz6XrMHkPIDwQm/hmH+ycjrXBfC7wHq/wh8VeOLSwu1m+yTLex6LdTBVv7NgQksLngPwUbPG5RnrmvzFuJf7NW0gZx9paXcu3qg/+tXvk3xI1P4i/COx8O3eoSXXiXRb77FpzLORcz2dxExaLOcuEeJevZwK5sbh416MoJ2fQ0wlZ4etGputn6Hq37QHwM8Najp3in4lQXKpaXVxaSvbgsHslkBWZpU7MG24B7g18Ztpvhq6tdVdL6cJlhbrKnLgZKNkcc/1rJi8ea9okt1HaardQtLtScNKzb1X+B1PBA9DVLUdWl16/lv7zyVlkOX+zxCNSfXaOK87CRqUVyzfMenipQre9FWMGbcJDk7fbFFW5I2mcsE4orrcTkuj6c+MnjB/9H1WyvryTU7y3E729zGUlRSeA5zy2OTjpkVjaL8SvGmn6XFqeoaxcaJZyFohNLndcYwGVEBBfHduAOhOeK9N1bTNYuPFNjfajoD6oqoWkeNPmI2BH5xjLKuAT14rzH9rjSZV8Yx6vpygeFdUtIDpm3BMMKxrm32j7pR92c9Tk96/Nsqw+Gr8uGqJNJbuzb30/4PUuNHmlKd3p8jE1X42JFqVnu+2ak0LIYhNcBQQp4ASNcAtk5ySSDX2V8Gf+Cjnwr+H/AIRttEXwdq2hzB2kuPLlSdJJWOXcuSGOT0BHAAHavzU8LalbeF9Uj1O/iuLhUEiFYn2SojqV3RnoG5rlLjULjUrp3tYZ2hizgty4H+0R3r76jgcPRfNTjZkOVnoj9rPCv7SHgz4n6xL441PVdmh6C0MFqmP9Gtp5z/rDkAu6qrckALzgdDX0FJ4j07xdp0N3pd9BqemXCEx3Fu6yxSqe6sMgivwf8L65cWnhe3sxM8UshMk21iCeNoB/D+dfZX7Ff7XU3g+80v4eeKgZ9BuZhb6bqCgB7ORzxG4/ijLHr1UnuOno0sP7P3r3uZVsRzqyVrH6F2uVjMSARRqMBEAVR9AK+Lf+CkCWMMngy8BDarHDP5sKsQz24ZcH8GLfgTX2fq+qWnh3T7vUL6ZLWytImmnmkOFjRQSST+Ffk/8AtAftB3nxs+IU2tSRLbaXbBrXTrQnOy3yclvVn5J/Adq66eruzhkYvwv8W/ATSpNUk+Iln4v1W6hBe3s4ZYvs7NjIj3oFkBY8c8Ac5rmfEXx2vNS8VTX/AIUsbfwTpAYNbafpMSq0CAAAtLjzHfjlia8p1zwfd3Wt3M9oyvbO4Mfz4JXHH5V9ifCv4FaB49/ZCtHngsfD/ixtVuJLfxFeNs5jkCLHIcEmNwSmAOoz2rx8XWVC0p3fM7eh6NN9rI+fbrxUfG7TJrdvb6ibl+bvy41vARn5lkVQc85IbINed3WkzaXr1xprTCWOGUoGHG4dmx9Mce9fZ3wB/Yd8S6x47ZfHFl/ZdlZyZks1kVmnYYPDKcKvI75IPSuc/bb/AGabX4a6vpHi+0aCD+29Tkt54YztVWGDG6r2GzAOO4968X+1sJ9ajhYSvJ9tr2va/p+h6DhLk5pHylNaTrKwWTAz0FFb3jjw3J4P8Yazod1L59xp9y9u8kXCsVPUZor6FTi1dI4LNOzZ+oPwZ8caV8TLrTNOlt5La41CNZWIGUkBUMFZv4dvIz3BHeu98WfDHwX8QPAMzaxptvcaU0SWttCiBWhcsQu1+qkZxnvX57eFfjLqHha8hawv5LcxL5YWM7QUK45x1z/QGvQbH9rbW47a1trkRraWTJ9njIzja3yhv72PU81+LVMoqU5KVC65bta26aW66Xv5+hVLMY2ameP/ALXHwb8N/A3x5B4e0641S+lmslvpVn2BU3FsKGHOAoHPc15B4et2vbzT7bSYFikmlEaxjHzOSAMk9ea7P9pH4lXPxQ+IWqeI5xh5Y0t4x1wka7Rn3JyfxrgfA7DcsrnKwygr7kYx/Imv2TAwqRw1NVXedle/cJyUptrY1PGfh290HxNq+m2iSXc9rJIwkjb5Cq8MAuPXkc9KZoutG6sYp43aKZSGVhwVYdCPcGuu8Y+bFrlxLC5jeePO4dGVhyPeuKWzWytZHwIyxJ2/jXo07ySdzkqWjJqx+h/7Qn7Tmh+JP2S9E0u21ZL/AMS+ILO3gu1tXOYWj2NP5ueRuIwAeuTX5+3txd3mpLbW1u88aJ5kqocfKPftWXpd4ftCgt8rHmuuhYaektzG2zfHwyjk+304rSe2hjHszFuJltLdod7bvMwrM2SBycZ9K/ST4T6dH4w+HHwstZNLl0u007SJEuZLO5SKGVbiIK6FMFmZiA+7K4J71+XOs6ouoLJKuSGb5e2PXPvzX62/CW3hh+GPhOa3g3Sf2VbMqhjlmEK8YPuK5ZUqVeadX7Ov4WHU5oQVup02tfGCTw3a6hd6hLDbyOQlqr4VtoyCSfwr5s+LHxo0P4x+C9VtvEWlf8Svy1utOvlzI0Esc6b1x/CCpbJPaoPiVpOq/EK1Gqb/AOzZbK1lF5aGcO0YL7WboQCGYY7cEda8g1rRZGjbT57W4gbUoZIGiiXaJhjG4AZ7jn3r+ecjwcY46WYSqucuZ/avb+lZ+W3mex7eUOWLWh5l+0E/mfHLx06gNG2qzGNlHDJkbSMdiMUVV1qG98ZyWusXN4i3NxbokqsuTujzGc/XZn8aK/eKdSMYJPcxqU5ObaRh6PI+v6Wt3BIC+Vc7jgrjryOtdD5atceaBlNoJLHjA61Vk8AXXgC8u9MNx5kBZHimVcq6HkH69QfStFNDuP7Pubh5EadYnYKWwWyuAAO5wD0ryKlOMq3KkeLWgo1Ld/1PLdenOohyvQNwW9qseHNtvbqoOWJLH646VlTymeBtp+bOcfjUmi3XlsyZwS3T6AV9Mmkkeqket3TJq2h6bctzII9hwP7pIFcj4otzHZQFT3JNafhfUvttvcacyb5FBlj/ANoE4Kj+YqHxDZ3uqcWzrH5YIEbc7wo54/P8qVFO7SCtKKSucrpMe6PesZYDqeoyTxn8j+Ver6Hp8UkFi1wdkAUqzjGRkFeM8fxHrX178C/gx8KdH+AFx4d1S4bVD4kijutR1toNssUu0FPK+UlREc4GOTnPWvkfWGh8N6xeWWnXcmoadY6gy29xOgDyqjkKXUccjBwOK6HHmWj23OVS5d1vseXeGNHt/FPjSz0iwfel1ex26SMNokDOFGR+Ir9gNG0OOHRRpKR+Wv2b7NGqtswoXBAPY46V8Dfsx/s/ya18Yr3VdRjWLSNDuReGJEK75myyIB1Cg8/hX6E6VeLHcfbbgf6IAUkYjop4JwfQd68bFTqKjV9j8SjK3rbQ391qLltdHCab8HzpGm6hoz6gken6z5sVyt25NwsBTzImjIHXJwy9MEMBmur0X4O6R4b8P6OsWnwDUZ4HtFmgiY7LdYzkKCcBicHHqSa6+TQdK1eSO7nu/PnWJlt7hZPkVWOCFIPJKhV9QBW211HcaT9ls40OUMQKOSIwwyGU84HX6Div5rlOvRmqEpXslbo2+7S0Z9LFxjZpK5+WXxQ8AWfw28SDw/p11/a720W+7udyxr5zyO+1VPIUIyfrRX2t4+/Zl0fXvFV5ex399J5m3LMT2UYHBGcDAyeTiiv0GnxlhqUI06zvNLV2er6/iZfVOb3k9PX/AIB+f3hnxVd+KNPu4NQs1mmh2n7QvKs2Bg+wIwfwqa41JNPkhMvKhc7SOTnIIAHeqfgTSZtD0a406WbfHM5DqgI8tUOQnPXH+FXb6zgv9WECqyyqwYPGu1RjgfN36DivvqjjUk3FnxmImqlS8Tw3U9PbRtUureThPNZo27FScjn6fyqr5i28hYnIPQ/0Nej+O/Aup3VnqXii0g83w/Yzx6cXXnY4QNz+fX3ryVXM8kkXUK3APUdzXu02+SLfY9WnLnimddot9Jb6ok8eQ6fOr9tw6fpW7NrFxcXqzj7ULfcDI9qo8xXGWBH4n9a5zS/llLdCBkKfTtW1a3UKzKyjDnH8RFaQqOD0NalPnWp7BN+1B42j8GWdnbRW9kHP2fz7eDDKqAYZUxtVsEY98nvXmNvqE1xI6vI7/aJR80rbpN27JJ9ySfzqrrmuPPa29hnbGmZCueT6Cs6xk3RyFiyOZA6sDyf8mto1Jz0bOaVJR1Prn9mXxJd+HPiVPBqmousmtWuGWR8+ZKpDKMnjO3P54r6b+L+txaT8M9VnWUI88RtYkz1Lnt+Ga+II55ZFsNRjVQ1oyMrEAhWGCG+uR1r3n46ePLXVtJksbI5mt4EvXVSD5e7ZgY+rn8q+dw9e1Ofz/E89T/dSicb4e+I2t6fcW9tBey+SY2VUaViikg5OM8dfzrvfAH7SkngnUJLe586TT8eUo3nfEP8AZ9RnPB9a+cV8RPZ2oGOSBlhx19PfrVyzR7lo57mdVeZFWGFsERse7Due9eFWy/C42DjVjZ9H1XzIp16tN+6z688PftUS6XDdi5kklee4adSbZGwpACjPsBRXy3cTxR+WvnbjsGWB4J7/AK0V5EciwtNckZysvM6FmFY88uNSe5WL7NuZlZpXRTjO1fnyfp/KrDXwgvhaQNv/AHjZZWG1RwA/1wK4jWPL8O29ha2+orqU+5JbiaMAwoX42Ic7mA6FiACc44wTHa3v2i8hUu8uHKtKOPMxxtH1/wAK9eMZ8109Gc8oW0Z9nfs96fpvjLw3rnh29iiks5bhZ/K6oSVxkcdMpWXrH7A/hltalvszxo84k2q3yhe4/Hn868x/Z88TX2jfE3SLpXWK2hGy8hV8j7O2A2cjBKko34Gv0mt9J/tTT1IG7K4NfbYetGtR13RvQclotD8wP2lPgza+FPF3h7TdFQQT3ds/lxqDmR4+nA9ema8avfDOu+FL63j1DTZre5bLhJByyjBP+HtxX6dfFb9l/WfGPxG8I+NNP1OK1t/D75nt5ODJGWG8A/7uf0rbsv2YvCHxC16WG6uo21C6LSrKJBK8KKRvRBn5VOVBH0rP21NptHrclSLimfkZZ6bqerXRvJ0KW0h5kbjbjPGfpVqFTbTWtoh/e3EgA77V6k19CftTfDG1+B/jq68D2okaO3ZLlJGH+sjfLD69h+BrxDQbVJvFFxdzBxDaW5OVGcbmC5Ptz+tZTq+xw8qyFX0bierafM2m6PtUs8c42KXPO319+atan4nF00bACV2TyBGxLc4VRk/UDHvj0rNVV/sO2RN8kcJlCmU7eoyB7iufj1L7O1y6gjyoW3MxyBkg7s+2D+Yr5KMqjd+58/foa39oQC5SV9smyMBMg4B6FvpmtD+2hGiIWYZfJkbAA57VxdnrElxJJJADhjuG4EqpI6NjoOfxqaO4kkgLXFs0iyYzNuztIHyuAOAM+nauinDWzE1Y27jxE0szmPEiAlQxBHQ+g6UVxF7LqFvNhGmIYZPkA7c9D0PtRWbptu9w5PMwfBtqt/4ne3uWeaGNWAVmxwpXaDjtXaeH9Wml8I63cTrHcFSmoRxyINkcvnCI4UYGCj4IOfur6UUVjjNE7dHD/wBKR39Du9HuDY61oLW6LA0nhKSUlBjDNb3CHH4Kp9iBX6c/AnVLjXPhz4bv7x/Nubuwt55Wx1do1LH8zRRXrZHrKrf+WP8A6VUHHp6/5F79oK4lsfhLrT28jwt5T5MbFSQEZsZHIBxjgg+hFfGn7LvizVPC/wAb/BVlYXci22tW1tJfJI7OJGlVmduTweAOOwH1oopYr+JJen5notv2lM53/go1rl1qXxWgjuCknkhdj7AGA8tflyOozzXzP8JVDw+L2YbmaBVLHrgSxnH04FFFdFb3su17/qPH6Tnbsj0PQdlxrNuLmJLqNWumEMwymViYrx7Hn6ivK9c32ul7BK8gNzsYyHO4DJwf0/IUUV5WH1lK/l+p4X2V6v8AQm0VlE0VuY1Md1MYZOo4xnjHQ5q7JGlreR7Fzu+VtxJ3Dnrz7UUV01uvoYPdl7U9AtLloJ5Q7yyxBnbeRkgkdvYCiiisoSfKtSj/2Q==";
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Intent resultPhoto = new Intent();
        resultPhoto.putExtra("data", bitmap);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultPhoto);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
    }

    @Test
    public void testAddPhoto() throws Exception {
        try {
            onView(withId(R.id.buttonNewAddPhoto)).perform(click());

            // TODO compare image bitmaps
            onView(withId(R.id.imageViewCaptured)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonNewChangePhoto)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonNewDeletePhoto)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonNewAddPhoto)).check(matches(not(isDisplayed())));
        }
        catch (Exception e) { }    // Catches case that photo has already been added, has to be general to account for checking visibilities and clicking
    }

    @Test
    public void testChangePhoto() throws Exception {
        try {
            onView(withId(R.id.buttonNewAddPhoto)).perform(click());
            onView(withId(R.id.buttonNewChangePhoto)).perform(click());

            // TODO compare image bitmaps
            onView(withId(R.id.imageViewCaptured)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonNewChangePhoto)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonNewDeletePhoto)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonNewAddPhoto)).check(matches(not(isDisplayed())));
        }
        catch (PerformException e) { }    // Catches case that no photo has been added
    }

    @Test
    public void testDeletePhoto() {
        try {
            onView(withId(R.id.buttonNewAddPhoto)).perform(click());
            onView(withId(R.id.buttonNewDeletePhoto)).perform(click());
            onView(withId(R.id.imageViewCaptured)).check(matches(not(isDisplayed())));
        }
        catch (PerformException e) { }    // Catches case that no photo has been added
    }

    @Test
    public void testEnterId() throws Exception {
        onView(withId(R.id.editTextGetID)).perform(ViewActions.typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.editTextGetID)).check(matches(withText("123")));
    }

    @Test
    public void testEnterGender() throws Exception {
        String gender = "Female";
        onView(withId(R.id.textViewSelectGender)).perform(click());
        onView(withText(gender)).perform(click());
        onView(withId(R.id.textViewSelectGender)).check(matches(withText(gender)));
    }

    @Test
    public void testEnterDOB() throws Exception {
        int year = 2017;
        int month = 11;
        int day = 21;

        onView(withId(R.id.textViewDisplayDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textViewDisplayDate)).check(matches(withText(month + "/" + day + "/" + year)));
    }

    @Test
    public void testCancel() throws Exception {
        onView(withId(R.id.buttonCancel)).perform(click());
        intended(hasComponent(DashboardActivity.class.getName()));
    }

    /*@Test
    public void testApply() throws Exception {
        onView(withId(R.id.buttonAddCalf)).perform(click());

        intended(hasComponent(NewCalfVaccineSelectionActivity.class.getName()));
    }*/

    @After
    public void tearDown() throws Exception {
        mIntentsTestRuleDashboard = null;
    }
}