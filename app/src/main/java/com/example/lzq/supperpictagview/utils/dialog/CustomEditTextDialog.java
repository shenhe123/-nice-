package com.example.lzq.supperpictagview.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzq.supperpictagview.R;

import org.greenrobot.eventbus.EventBus;


/**
 * 
 * Create custom Dialog windows for your application Custom dialogs rely on
 * custom layouts wich allow you to create and use your own look & feel.
 * 
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * <a href="http://my.oschina.net/arthor" target="_blank"
 * rel="nofollow">@author</a> antoine vianey
 * 
 */
public class CustomEditTextDialog extends Dialog {

	public CustomEditTextDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomEditTextDialog(Context context) {
		super(context);
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {

		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private String share;
		private View contentView;
		private int type;

		private OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;
		private EditText editText;
		public CallBack callBack;
		private TextView tv_brand;

		public Builder(Context context, int type, CallBack callBack) {
			this.context = context;
			this.type=type;
			this.callBack=callBack;
		}

		/**
		 * Set the Dialog message from String
		 *
		 * @param
		 * @return
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 *
		 * @param
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 *
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 *
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 *
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 *
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		/*
		 * public Builder setPositiveButton(int positiveButtonText,
		 * DialogInterface.OnClickListener listener) { this.positiveButtonText =
		 * (String) context .getText(positiveButtonText);
		 * this.positiveButtonClickListener = listener; return this; }
		 */

		/**
		 * Set the positive button text and it's listener
		 *
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 *
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		/*
		 * public Builder setNegativeButton(int negativeButtonText,
		 * DialogInterface.OnClickListener listener) { this.negativeButtonText =
		 * (String) context .getText(negativeButtonText);
		 * this.negativeButtonClickListener = listener; return this; }
		 */

		/**
		 * Set the negative button text and it's listener
		 *
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setText(String share) {
			editText.setText(share);
			return this;
		}

		/**
		 * Create the custom dialog
		 */
		public CustomEditTextDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// instantiate the dialog with the custom Theme
			final CustomEditTextDialog dialog = new CustomEditTextDialog(context,
					R.style.Dialog);

			dialog.setCanceledOnTouchOutside(false);
			final View layout = inflater.inflate(R.layout.custom_dialog_edit, null);
			editText=((EditText) layout.findViewById(R.id.message));
			tv_brand=((TextView) layout.findViewById(R.id.tv_brand));
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			if (title != null) {
				((TextView) layout.findViewById(R.id.title)).setText(title);
			}
			if (type==1){
				tv_brand = ((TextView) layout.findViewById(R.id.tv_brand));
				tv_brand.setVisibility(View.VISIBLE);
				tv_brand.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(context,"选择品牌", Toast.LENGTH_SHORT).show();
						//跳转到选择品牌页面 回来设置给tv_brand
						tv_brand.setText("");
					}
				});
			}

			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);

//									callBack.call(tv_brand.getText().toString(),editText.getText().toString());
									EventBus.getDefault().post(new EventClass(editText.getText().toString()));
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);

								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			dialog.setContentView(layout);
			return dialog;
		}
		public static class EventClass{
			public String edit;
			EventClass(String edit){
				this.edit=edit;
			}
		}
        public CustomEditTextDialog create02(){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final CustomEditTextDialog dialog = new CustomEditTextDialog(context,
                    R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.custom_dialog_layout02, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            if (title != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            }
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);

                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }

            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(
                        contentView, new LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);

            return dialog;
        }

	}
	public interface CallBack{
		void call(String brand, String share);
	}

}