package com.example.admin.caipiao33.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.example.admin.caipiao33.R;


/**
 * 自定义进度条
 *
 * @author Administrator
 */
public class ProgressDialogBar extends Dialog
{
    public static long first_time;
    public static ProgressDialogBar ProgressDialogBar = null;

    public ProgressDialogBar(Context context)
    {
        super(context);
    }

    public ProgressDialogBar(Context context, int theme)
    {
        super(context, theme);
    }

    public static ProgressDialogBar createDialog(Context context)
    {
        ProgressDialogBar = new ProgressDialogBar(context, R.style.MyAlertDialog);
        ProgressDialogBar.setContentView(R.layout.progress_layout);
        return ProgressDialogBar;
    }

    /*  public void onWindowFocusChanged(boolean hasFocus){

          if (ProgressDialogBar == null){
              return;
          }
        //  ImageView imageView = (ImageView) ProgressDialogBar.findViewById(R.id.img);//////
          //AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
          //animationDrawable.start();
      }

      /**
       *
       * [Summary]
       *       setTitile 标题
       * @param strTitle
       * @return
       *
       */
    public ProgressDialogBar setTitile(String strTitle)
    {
        return ProgressDialogBar;
    }

    /**
     * [Summary]
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public void setProgressMsg(String strMessage)
    {
        TextView tvMsg = (TextView) ProgressDialogBar.findViewById(R.id.text_progress);
        if (tvMsg != null)
            tvMsg.setText(strMessage);
    }

    @Override
    public void show()
    {
        first_time = System.currentTimeMillis();
        super.show();
    }

    public void dismiss(final Handler handler, final int flag)
    {
        final long temp = System.currentTimeMillis() - first_time;
        if (temp > 2000)
        {
            super.dismiss();
            handler.sendEmptyMessage(flag);
        }
        else
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(2000 - temp);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    ProgressDialogBar.super.dismiss();
                    handler.sendEmptyMessage(flag);
                }
            }).start();
        }
    }
}
