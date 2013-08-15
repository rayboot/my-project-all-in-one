package com.rayboot.weatherpk;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class FalshActivity extends Activity {
	@InjectView(R.id.tvYou)
	TextView tvYou;
	@InjectView(R.id.tvAnd)
	TextView tvAnd;
	@InjectView(R.id.tvMe)
	TextView tvMe;
	@InjectView(R.id.tvPK)
	TextView tvPK;
	@InjectView(R.id.btnJump)
	Button btnJump;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_falsh);
		Views.inject(this);
		startAnimation();
//		finishAnimation();
		btnJump.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishAnimation();
			}
		});
	}

	private void startAnimation() {

		ObjectAnimator.ofFloat(tvYou, "alpha", 0, 1).setDuration(4000).start();
		ObjectAnimator theTranslationX = ObjectAnimator.ofFloat(tvYou,
				"translationX", 0, 25, 50).setDuration(4000);
		theTranslationX.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				ObjectAnimator alpha = ObjectAnimator.ofFloat(tvAnd, "alpha",
						0, 1).setDuration(4000);
				alpha.addListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator arg0) {
						// TODO Auto-generated method stub

						ObjectAnimator.ofFloat(tvMe, "alpha", 0, 1)
								.setDuration(4000).start();
						ObjectAnimator theTranslationX = ObjectAnimator
								.ofFloat(tvMe, "translationX", 0, -25, -50)
								.setDuration(4000);
						theTranslationX.addListener(new AnimatorListener() {

							@Override
							public void onAnimationStart(Animator arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationRepeat(Animator arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationEnd(Animator arg0) {
								// TODO Auto-generated method stub
								ObjectAnimator alpha = ObjectAnimator.ofFloat(
										tvPK, "alpha", 0, 1).setDuration(1000);
								alpha.addListener(new AnimatorListener() {

									@Override
									public void onAnimationStart(Animator arg0) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onAnimationRepeat(Animator arg0) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onAnimationEnd(Animator arg0) {
										// TODO Auto-generated method stub
										finishAnimation();
									}

									@Override
									public void onAnimationCancel(Animator arg0) {
										// TODO Auto-generated method stub

									}
								});
								alpha.start();
								tvPK.setVisibility(View.VISIBLE);
								Random random = new Random();
								random.nextInt(50);
								ObjectAnimator
										.ofFloat(tvPK, "translationX", 0,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25, 0)
										.setDuration(1000).start();
								ObjectAnimator
										.ofFloat(tvPK, "translationY", 0,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25,
												random.nextInt(50) - 25, 0)
										.setDuration(1000).start();
							}

							@Override
							public void onAnimationCancel(Animator arg0) {
								// TODO Auto-generated method stub

							}
						});
						theTranslationX.start();
						tvMe.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationCancel(Animator arg0) {
						// TODO Auto-generated method stub

					}
				});
				alpha.start();
				tvAnd.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub

			}
		});
		theTranslationX.start();
	}

	private void finishAnimation() {
		finish();
//		this.startActivity(new Intent(this, MainActivity.class));
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	}
}