package com.example.oliverbaird.finda5aside;

import android.annotation.SuppressLint;
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

    TextView textviewLocation, textviewTime, textviewSpaces, textviewCost, textviewDate, textViewSkill;

    TextView txtViewLocationtext, txtViewCosttext, txtViewSpacestext, txtViewTimetext, txtViewDatetext, txtViewSkilltext;

    public GamesList(Activity context, List<GameDB> gameList){
        super(context, R.layout.game_view, gameList);
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.game_view, null, true);

        textviewLocation = listViewItem.findViewById(R.id.textviewLocation);
        textviewTime = listViewItem.findViewById(R.id.textviewTime);
        textviewSpaces = listViewItem.findViewById(R.id.textviewSpaces);
        textviewCost = listViewItem.findViewById(R.id.textviewCost);
        textviewDate = listViewItem.findViewById(R.id.textviewDate);
        textViewSkill = listViewItem.findViewById(R.id.textviewSkill);

        txtViewLocationtext = listViewItem.findViewById(R.id.txtViewLocationtext);
        txtViewCosttext = listViewItem.findViewById(R.id.txtViewCosttext);
        txtViewSpacestext = listViewItem.findViewById(R.id.txtViewSpacestext);
        txtViewTimetext = listViewItem.findViewById(R.id.txtViewTimetext);
        txtViewDatetext = listViewItem.findViewById(R.id.txtViewDatetext);
        txtViewSkilltext = listViewItem.findViewById(R.id.txtViewSkilltext);

        GameDB game = gameList.get(position);

        textviewLocation.setText(game.getGameLocation());
        textviewCost.setText(game.getGameCost());
        textviewTime.setText(game.getGameTime());
        textviewSpaces.setText(game.getGameSpaces());
        textviewDate.setText(game.getGameDate());
        textViewSkill.setText(game.getSkill());

        txtViewLocationtext.setText("Location:");
        txtViewCosttext.setText("Cost:");
        txtViewTimetext.setText("Time:");
        txtViewSpacestext.setText("Spaces available:");
        txtViewDatetext.setText("Date:");
        txtViewSkilltext.setText("Skill level:");

        return listViewItem;

    }
}
