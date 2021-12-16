package com.example.newsapp38m4.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp38m4.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[] {"a", "b", "c"};
    private String[] subtitles = new String[] {"sub a", "sub b", "sub c"};
    private int[] images = new int[] {R.drawable.ic_newspage, R.drawable.img_newspaper, R.drawable.img_news_globus};

    private NavController navController;

    public BoardAdapter(NavController navController) {
        this.navController = navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvSubtitle, tvSkip;
        private ImageView imageView, ivFirstDot, ivSecondDot, ivThirdDot;
        private Button btnStart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_subtitle);
            imageView = itemView.findViewById(R.id.image_view);
            btnStart = itemView.findViewById(R.id.btn_start);

            ivFirstDot = itemView.findViewById(R.id.iv_first_dot);
            ivSecondDot = itemView.findViewById(R.id.iv_second_dot);
            ivThirdDot = itemView.findViewById(R.id.iv_third_dot);

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigateUp();
                }
            });
        }

        public void onBind(int position) {
            tvTitle.setText(titles[position]);
            tvSubtitle.setText(subtitles[position]);
            imageView.setImageResource(images[position]);

            if (position == titles.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else btnStart.setVisibility(View.INVISIBLE);
        }
    }
}
