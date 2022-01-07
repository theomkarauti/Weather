package truesoft.app.weather.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import truesoft.app.weather.R;
import truesoft.app.weather.model.TempData;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.TData>{


    // Adapter class is used for recycler view of 5 days data...

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

        // call temp data class and get exact min max Temp...

        TempData tempData = tempDataList.get(position);

        //for(int i=0; i<=5;i++)
      //  {


            holder.date.setText(String.valueOf(tempData.getList().get(0).getDtTxt()));
            holder.mintemp.setText(String.valueOf(tempData.getList().get(0).getMain().getTempMin()));
            holder.maxtemp.setText(String.valueOf(tempData.getList().get(0).getMain().getTempMax()));


        //}



//        Log.d("Datas",""+tempData.getCod());
    }

    @Override
    public int getItemCount() {
        Log.d("size",""+tempDataList.size());
        return tempDataList.size();
    }



    // Tdata used to set as view holder of

    public class TData extends RecyclerView.ViewHolder {
        TextView date,mintemp,maxtemp;

        public TData(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            mintemp = itemView.findViewById(R.id.mintemp);
            maxtemp = itemView.findViewById(R.id.max_temperature);
        }
    }
}
