package com.example.newsapp38m4.ui.board;

import android.content.Context;
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

import com.example.newsapp38m4.Prefs;
import com.example.newsapp38m4.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[] {"Добро пожаловать!", "Новости всегда и везде!", "Новости в прямом эфире!"};
    private String[] subtitles = new String[] {"В лучший новостной портал", "Все необходимые новости под рукой", "Прямо из приложения, без использования сторонних сервисов"};
    private int[] images = new int[] {R.drawable.ic_newspage, R.drawable.img_newspaper, R.drawable.img_news_globus};

    private NavController navController;
    private Context context;

    public BoardAdapter(NavController navController) {
        this.navController = navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
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

        private TextView tvTitle, tvSubtitle, btnSkip;
        private ImageView imageView;
        private Button btnStart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_subtitle);
            imageView = itemView.findViewById(R.id.image_view);
            btnStart = itemView.findViewById(R.id.btn_start);
            btnSkip = itemView.findViewById(R.id.tv_skip_board);

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Prefs(context).saveBoardState();
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
