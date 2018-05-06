package com.fastpack.fastpackandroid.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fastpack.fastpackandroid.R;

public class UtilsAnimation {
    public static void animarWithAnimationUtils(View view ) {
        Animation animFadein = AnimationUtils.loadAnimation( view.getContext(), android.R.anim.accelerate_decelerate_interpolator );
        view.startAnimation(animFadein);
    }

    public static void efecctInView(View v, int effect) {
        Techniques swing = null;
        switch (effect) {
            case 0:
                swing = Techniques.Swing;
                break;
            case 1:
                swing = Techniques.Tada;
                break;
            case 2:
                swing = Techniques.Pulse;
                break;
            case 3:
                //efeito de saida da view
                swing = Techniques.SlideInRight;
                break;
            case 4:
                //efeito de entrada de uma view
                swing = Techniques.SlideInLeft;
                break;
            case 5:
                swing = Techniques.SlideOutRight;
                break;
            case 6:
                swing = Techniques.SlideOutUp;
                break;
            case 7:
                swing = Techniques.StandUp;
                break;
            case 8:
                swing = Techniques.TakingOff;
                break;
            case 9:
                swing = Techniques.FadeInRight;
                break;
            case 10:
                swing = Techniques.SlideInUp;
                break;
            case 11:
                swing = Techniques.SlideOutDown;
                break;

        }
        try {
            YoYo.with(swing)
                    .duration(1000)
                    .playOn(v);
        } catch (Exception e) {
        }

    }

    // slide the view from below itself to the current position
    public static void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(400);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public static void slideDown(View view, final @Nullable ListenerEffect listener) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(400);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null)
                    listener.onEffectTermined();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from left to right
    public static void slideRight(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);

    }

    // To animate view slide out from right to left
    public static void slideLeft(View view) {
        slideLeft( view , null );
    }

    public static void slideLeft(View view, @Nullable final ListenerEffect listener) {
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(1500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) listener.onEffectTermined();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);

    }

    public interface ListenerEffect {
        void onEffectTermined();
    }

    @RequiresApi(21)
    public static void effectCircle(final ViewGroup container, final View view, final ListenerEffect onIntermedial) {
        effectCircle(container, view, true, new ListenerEffect() {
            @Override
            public void onEffectTermined() {
                onIntermedial.onEffectTermined();

                effectCircle(container, view, false, null);

            }
        });

    }

    private static View getViewByEffect(Context context, int idColor) {
        View view = new View(context);
        view.setBackgroundColor(idColor);
        view.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        return view;
    }


    public static void effectCircle(View viewEffect, View viewCalculo, boolean isAbertura, @Nullable final ListenerEffect effectListener) {
        if (Build.VERSION.SDK_INT >= 21) {
            // get the center for the clipping circle
            int cx = (viewCalculo.getLeft() + viewCalculo.getRight()) / 2;
            int cy = (viewCalculo.getTop() + viewCalculo.getBottom()) / 2;

// get the initial radius for the clipping circle
            int initialRadius = viewCalculo.getWidth();

            int finalRadius = Math.max(viewEffect.getWidth(), viewEffect.getHeight());

// create the animation (the final radius is zero)
            Animator anim;
            if (isAbertura)
                anim =
                        ViewAnimationUtils.createCircularReveal(viewEffect, cx, cy, finalRadius, 0);
            else
                anim =
                        ViewAnimationUtils.createCircularReveal(viewEffect, cx, cy, 0, finalRadius);


            anim.setDuration(350);

// make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (effectListener != null)
                        effectListener.onEffectTermined();
                }
            });

// make the view visible and start the animation
            viewEffect.setVisibility(View.VISIBLE);
            anim.start();
        }

    }

    //entrada
    public static void slideToRightActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_rigth);
    }

    //saida
    public static void slideToLeftActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_to_rigth , R.anim.slide_to_left);
    }

}
