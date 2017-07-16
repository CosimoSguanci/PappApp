package app.android.com.pappapppaid;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.List;

/**
 * Created by shind on 26/03/2017.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<RecipeItem> list;
    private Context ctx;

    public SearchResultsAdapter(List<RecipeItem> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.card_view_staggered, parent, false);
        SearchResultsAdapter.ViewHolder viewHolder = new SearchResultsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchResultsAdapter.ViewHolder viewHolder, int position) {
        RecipeItem item = list.get(position);
        TextView textView = viewHolder.text;
        textView.setText(item.getName());
        ImageView imageView = viewHolder.img;

        String [] strParts = item.getName().split(" ");
        for(int i = 0; i<strParts.length; i++){
            strParts[i] = strParts[i]+"_";
            strParts[i] = strParts[i].toLowerCase();
        }

        strParts[strParts.length-1] = strParts[strParts.length-1].replace("_","");
        String resName = "";

        for (String s: strParts){
            resName +=s;
        }

        resName = Normalizer.normalize(resName, Normalizer.Form.NFD);
        resName = resName.replaceAll("[^\\p{ASCII}]", "");
        resName = resName.replaceAll("'", "");
        resName = resName.replaceAll(",", "");
        resName = resName.replaceAll("[()]", "");

       /* switch(item.getType()){
            case "Colazione":
                imageView.setImageResource(R.drawable.colazione);
                break;
            case "Pranzo":
                imageView.setImageResource(R.drawable.pranzo);
                break;
            case "Antipasto":
                imageView.setImageResource(R.drawable.pranzo);
                break;
            case "Primo":
                imageView.setImageResource(R.drawable.pranzo);
                break;
            case "Spuntino":
                imageView.setImageResource(R.drawable.snack);
                break;
            case "Frutta":
                imageView.setImageResource(R.drawable.snack);
                break;
            case "Cena":
                imageView.setImageResource(R.drawable.cena);
                break;
            case "Secondo":
                imageView.setImageResource(R.drawable.cena);
                break;
            case "Cena(secondo piatto)":
                imageView.setImageResource(R.drawable.cena);
                break;
            case "Dessert":
                imageView.setImageResource(R.drawable.dessert);
                break;
            case "Desser":
                imageView.setImageResource(R.drawable.dessert);
                break;
            case "Dolce":
                imageView.setImageResource(R.drawable.dessert);
                break;
            case "dolce":
                imageView.setImageResource(R.drawable.dessert);
                break;
            case "Aperitivo":
                imageView.setImageResource(R.drawable.brunch);
                break;

        }*/

        new LoadImageRecyclerView(imageView, resName, item.getType()).execute();

        /*int test = ctx.getResources().getIdentifier(resName, "drawable", ctx.getPackageName());

        if(test != 0){
            imageView.setImageResource(test);
        }
        else{
            switch(item.getType()){
                case "Colazione":
                    imageView.setImageResource(R.drawable.colazione);
                    break;
                case "Pranzo":
                    imageView.setImageResource(R.drawable.pranzo);
                    break;
                case "Antipasto":
                    imageView.setImageResource(R.drawable.pranzo);
                    break;
                case "Primo":
                    imageView.setImageResource(R.drawable.pranzo);
                    break;
                case "Spuntino":
                    imageView.setImageResource(R.drawable.snack);
                    break;
                case "Frutta":
                    imageView.setImageResource(R.drawable.snack);
                    break;
                case "Cena":
                    imageView.setImageResource(R.drawable.cena);
                    break;
                case "Secondo":
                    imageView.setImageResource(R.drawable.cena);
                    break;
                case "Cena(secondo piatto)":
                    imageView.setImageResource(R.drawable.cena);
                    break;
                case "Dessert":
                    imageView.setImageResource(R.drawable.dessert);
                    break;
                case "Desser":
                    imageView.setImageResource(R.drawable.dessert);
                    break;
                case "Dolce":
                    imageView.setImageResource(R.drawable.dessert);
                    break;
                case "dolce":
                    imageView.setImageResource(R.drawable.dessert);
                    break;
                case "Aperitivo":
                    imageView.setImageResource(R.drawable.brunch);
                    break;

            }
        }*/

        /*int test = ctx.getResources().getIdentifier(resName, "drawable", ctx.getPackageName());

        if(test != 0){
            Glide.with(ctx).load("").placeholder(test).into(imageView);
        }
        else{
            switch(item.getType()){
                case "Colazione":
                    Glide.with(ctx).load("").placeholder(R.drawable.colazione).into(imageView);
                    break;
                case "Pranzo":
                    Glide.with(ctx).load("").placeholder(R.drawable.pranzo).into(imageView);
                    break;
                case "Antipasto":
                    Glide.with(ctx).load("").placeholder(R.drawable.pranzo).into(imageView);
                    break;
                case "Primo":
                    Glide.with(ctx).load("").placeholder(R.drawable.pranzo).into(imageView);
                    break;
                case "Spuntino":
                    Glide.with(ctx).load("").placeholder(R.drawable.snack).into(imageView);
                    break;
                case "Frutta":
                    Glide.with(ctx).load("").placeholder(R.drawable.snack).into(imageView);
                    break;
                case "Cena":
                    Glide.with(ctx).load("").placeholder(R.drawable.cena).into(imageView);
                    break;
                case "Secondo":
                    Glide.with(ctx).load("").placeholder(R.drawable.cena).into(imageView);
                    break;
                case "Cena(secondo piatto)":
                    Glide.with(ctx).load("").placeholder(R.drawable.cena).into(imageView);
                    break;
                case "Dessert":
                    Glide.with(ctx).load("").placeholder(R.drawable.dessert).into(imageView);
                    break;
                case "Desser":
                    Glide.with(ctx).load("").placeholder(R.drawable.dessert).into(imageView);
                    break;
                case "Dolce":
                    Glide.with(ctx).load("").placeholder(R.drawable.dessert).into(imageView);
                    break;
                case "dolce":
                    Glide.with(ctx).load("").placeholder(R.drawable.dessert).into(imageView);
                    break;
                case "Aperitivo":
                    Glide.with(ctx).load("").placeholder(R.drawable.brunch).into(imageView);
                    break;

            }
        }*/


    }

    class LoadImageRecyclerView extends AsyncTask<Void, Void, ImageView>{
        private ImageView imageView;
        private String recipeName;
        private String recipeType;
        private int test;

        public LoadImageRecyclerView(ImageView imageView, String recipeName, String recipeType){
            this.imageView = imageView;
            this.recipeName = recipeName;
            this.recipeType = recipeType;
        }
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected ImageView doInBackground(Void... params) {
            //test = ctx.getResources().getIdentifier(recipeName, "drawable", ctx.getPackageName());

            test = ctx.getResources().getIdentifier(recipeName, "mipmap", ctx.getPackageName());

            return imageView;

        }

        protected void onPostExecute(ImageView imageView){
            if(test != 0){
                imageView.setImageResource(test);
                //Glide.with(ctx).load("").placeholder(test).into(imageView);
            }
            else{
                switch(recipeType){
                    case "Colazione":
                        imageView.setImageResource(R.drawable.colazione);
                        break;
                    case "Pranzo":
                        imageView.setImageResource(R.drawable.pranzo);
                        break;
                    case "Antipasto":
                        imageView.setImageResource(R.drawable.pranzo);
                        break;
                    case "Primo":
                        imageView.setImageResource(R.drawable.pranzo);
                        break;
                    case "Spuntino":
                        imageView.setImageResource(R.drawable.snack);
                        break;
                    case "Frutta":
                        imageView.setImageResource(R.drawable.snack);
                        break;
                    case "Cena":
                        imageView.setImageResource(R.drawable.cena);
                        break;
                    case "Secondo":
                        imageView.setImageResource(R.drawable.cena);
                        break;
                    case "Cena(secondo piatto)":
                        imageView.setImageResource(R.drawable.cena);
                        break;
                    case "Dessert":
                        imageView.setImageResource(R.drawable.dessert);
                        break;
                    case "Desser":
                        imageView.setImageResource(R.drawable.dessert);
                        break;
                    case "Dolce":
                        imageView.setImageResource(R.drawable.dessert);
                        break;
                    case "dolce":
                        imageView.setImageResource(R.drawable.dessert);
                        break;
                    case "Aperitivo":
                        imageView.setImageResource(R.drawable.brunch);
                        break;

                }
            }

        }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            text = (TextView) itemView.findViewById(R.id.high_textviewcard);
            img = (ImageView) itemView.findViewById(R.id.meal_image);
        }

        @Override
        public void onClick(View view){
            ((Activity) ctx).getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SingleRecipeFragment.newInstance(list.get(getAdapterPosition()).getName(), list.get(getAdapterPosition()).getPrep(), list.get(getAdapterPosition()).getTimePrep())).addToBackStack(null).commit();
        }

    }




    private Context getContext(){
        return ctx;
    }

}
