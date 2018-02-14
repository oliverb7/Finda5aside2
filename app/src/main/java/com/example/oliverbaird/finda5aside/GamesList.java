package com.example.oliverbaird.finda5aside;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by oliverbaird on 12/02/2018.
 */

public class GamesList extends ArrayAdapter<GameDB> {

    private Activity context;
    private List<GameDB> gameList;

    public GamesList(Activity context, List<GameDB> gameList){
        super(context, R.layout.game_view, gameList);
        this.context = context;
        this.gameList = gameList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.game_view, null, true);

        TextView textviewLocation = (TextView) listViewItem.findViewById(R.id.textviewLocation);
        TextView textviewTime = (TextView) listViewItem.findViewById(R.id.textviewTime);
        TextView textviewSpaces = (TextView) listViewItem.findViewById(R.id.textviewSpaces);
        TextView textviewCost = (TextView) listViewItem.findViewById(R.id.textviewCost);


        TextView txtViewLocationtext = (TextView) listViewItem.findViewById(R.id.txtViewLocationtext);
        TextView txtViewCosttext = (TextView) listViewItem.findViewById(R.id.txtViewCosttext);
        TextView txtViewSpacestext = (TextView) listViewItem.findViewById(R.id.txtViewSpacestext);
        TextView txtViewTimetext = (TextView) listViewItem.findViewById(R.id.txtViewTimetext);


        GameDB game = gameList.get(position);

        textviewLocation.setText(game.getGameLocation());
        textviewCost.setText(game.getGameCost());
        textviewTime.setText(game.getGameTime());
        textviewSpaces.setText(game.getGameSpaces());

        txtViewLocationtext.setText("Location:");
        txtViewCosttext.setText("Cost:");
        txtViewTimetext.setText("Time:");
        txtViewSpacestext.setText("Spaces available:");





        return listViewItem;

    }
}
