package truesoft.app.weather;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.TData>{

    List<TempData> tempDataList;

    public TempAdapter(List<TempData> tempDataList) {
        this.tempDataList = tempDataList;
    }

    @NonNull
    @Override
    public TempAdapter.TData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new TData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempAdapter.TData holder, int position) {
        TempData tempData = tempDataList.get(position);

        holder.date.setText(String.valueOf(tempData.getCod()));
        holder.mintemp.setText(String.valueOf(tempData.getMessage()));
//        Log.d("Datas",""+tempData.getCod());
    }

    @Override
    public int getItemCount() {
        return tempDataList.size();
    }

    public class TData extends RecyclerView.ViewHolder {
        TextView date,mintemp;

        public TData(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            mintemp = itemView.findViewById(R.id.mintemp);
        }
    }
}
