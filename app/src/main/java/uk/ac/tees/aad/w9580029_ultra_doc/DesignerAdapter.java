package uk.ac.tees.aad.w9580029_ultra_doc;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.aad.w9580029_ultra_doc.model.RecyclerModel;

public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.ViewHolder> {

    public List<RecyclerModel> recyclerModelList;
    public static String layout_inflate_type;

    private final int TITLE_VIEW = 1;
    private final int CALENDER_VIEW = 2;
    private final int IMAGE_VIEW = 3;
    private final int LOCATION_VIEW =4;

    public DesignerAdapter(List<RecyclerModel> recyclerModelList) {
        Log.d("MethodNm:", "+++++++++++ constructor DesignerAdapter +++++++++");
        this.recyclerModelList = recyclerModelList;
    }


    public static void setLayoutInflate(String cur_layout_xml) {
        layout_inflate_type = cur_layout_xml;
    }

    public void updateData(List<RecyclerModel> recyclerModelList) {
        this.recyclerModelList = recyclerModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DesignerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MethodNm:", "+++++++++++ onCreateViewHolder +++++++++>>" + viewType);
        View cur_view = null;

        //inflate the title_item xml
        if (viewType == TITLE_VIEW) {
            cur_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item_design, parent, false);
        }
        //inflate calender_item xml
        else if (viewType == CALENDER_VIEW) {
            cur_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_item_design, parent, false);
        } else if (viewType == IMAGE_VIEW) {
            cur_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_design, parent, false);
        }
        else if(viewType == LOCATION_VIEW){
            cur_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item_design, parent, false);
        }

        return new ViewHolder(cur_view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignerAdapter.ViewHolder holder, int position) {
        Log.d("MethodNm:", "+++++++++++ onBindViewHolder +++++++++currPosition" + String.valueOf(position));

        if (holder.getItemViewType() == TITLE_VIEW) {
            String title_desc = recyclerModelList.get(position).getTitle_description();
            holder.setTitleData(title_desc);
        } else if (holder.getItemViewType() == CALENDER_VIEW) {
            String calender_date = recyclerModelList.get(position).getCurrent_date();
            holder.setCalenderData(calender_date);
        } else if (holder.getItemViewType() == IMAGE_VIEW) {
            Bitmap imageBitMap = recyclerModelList.get(position).getImageUrl();
            holder.setImageData(imageBitMap);
        }
        else if(holder.getItemViewType() == LOCATION_VIEW){
            Bitmap mapBitMap = recyclerModelList.get(position).getLocationURL();
            holder.setMapData(mapBitMap);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String title_view = recyclerModelList.get(position).getTitle_description();
        String calander_view = recyclerModelList.get(position).getCurrent_date();
        boolean is_image_view = recyclerModelList.get(position).getImageCapture();
        boolean is_location_view = recyclerModelList.get(position).getLocationCapture();
        Log.d("ISIMAGE", "===>>>>" + is_image_view);

        Log.d("ISLOcation", "===>>>>" + is_location_view);
        if (title_view != null && title_view.trim().length() > 0) {
            return TITLE_VIEW;
        } else if (calander_view != null && calander_view.trim().length() > 0) {
            return CALENDER_VIEW;
        } else if (is_image_view) {
            return IMAGE_VIEW;
        }else if (is_location_view){
            return LOCATION_VIEW;
        }
        else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {

        return recyclerModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_desc_textView;
        private TextView date_left_tv, date_center_tv, date_right_tv;
        private ImageView imageViewCapture,mapViewCapture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_desc_textView = itemView.findViewById(R.id.title_description);
            date_left_tv = itemView.findViewById(R.id.date_tv_left);
            date_center_tv = itemView.findViewById(R.id.date_tv_center);
            date_right_tv = itemView.findViewById(R.id.date_tv_right);
            imageViewCapture = itemView.findViewById(R.id.imageView_capture);
            mapViewCapture = itemView.findViewById(R.id.mapImage_capture);
        }

        public void setTitleData(String title_desc) {
            Log.d("MethodNm:", "+++++++++++ setData +++++++++>>>>" + title_desc);
            title_desc_textView.setText(title_desc);
        }

        public void setCalenderData(String choosed_date_position) {
            Log.d("MethodNm:", "+++++++++++ setCalenderData +++++++++>>>>" + choosed_date_position);
            String cur_date = choosed_date_position.split("~~")[0].toString();
            String date_pos = choosed_date_position.split("~~")[1].toString();
            if (date_pos.equalsIgnoreCase("left")) {
                date_left_tv.setVisibility(View.VISIBLE);
                date_left_tv.setText(cur_date);

            } else if (date_pos.equalsIgnoreCase("center")) {
                date_center_tv.setVisibility(View.VISIBLE);
                date_center_tv.setText(cur_date);

            } else if (date_pos.equalsIgnoreCase("right")) {
                date_right_tv.setVisibility(View.VISIBLE);
                date_right_tv.setText(cur_date);

            } else {
                //not position choose.

            }

        }

        public void setImageData(Bitmap imageBitMap) {
            Log.d("MethodNm:", "+++++++++++ setImageData +++++++++>>>>" + imageBitMap);
            imageViewCapture.setImageBitmap(imageBitMap);
        }

        public void setMapData(Bitmap mapBitMap) {
            Log.d("MethodNm:", "+++++++++++ setMapData +++++++++>>>>" + mapBitMap);
            mapViewCapture.setImageBitmap(mapBitMap);
        }
    }
}
