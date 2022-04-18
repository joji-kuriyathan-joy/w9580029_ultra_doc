package uk.ac.tees.aad.w9580029_ultra_doc;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.aad.w9580029_ultra_doc.model.RecyclerModel;

public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.ViewHolder> {

    public List<RecyclerModel> recyclerModelList;

    public DesignerAdapter(List<RecyclerModel> recyclerModelList){
        Log.d("MethodNm:","+++++++++++ constructor DesignerAdapter +++++++++");
        this.recyclerModelList = recyclerModelList;
    }


    public void updateData(List<RecyclerModel> recyclerModelList){
        this.recyclerModelList = recyclerModelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DesignerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MethodNm:","+++++++++++ onCreateViewHolder +++++++++");
        View title_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item_design,parent,false);
        return new ViewHolder(title_view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignerAdapter.ViewHolder holder, int position) {
        Log.d("MethodNm:","+++++++++++ onBindViewHolder +++++++++");
        String title_desc =  recyclerModelList.get(position).getTitle_description();
        holder.setData(title_desc);
    }

    @Override
    public int getItemCount() {

        return recyclerModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_desc_textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_desc_textView = itemView.findViewById(R.id.title_description);
        }

        public void setData(String title_desc) {
            Log.d("MethodNm:","+++++++++++ setData +++++++++>>>>"+title_desc);
            title_desc_textView.setText(title_desc);
        }
    }
}
