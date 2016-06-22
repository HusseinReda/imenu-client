package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.OrderCard;
import com.sparta.imenu_client.service.Auxiliary;

/**
 * Created by Hussein Abu Maash on 6/17/2016.
 */

public class QuantityPickerDialog {
    RelativeLayout relativeLayout;
    Context context;
    boolean inOrderDialog;
    final NumberPicker numberPicker;
    AlertDialog alertDialog;
    Item item;

    public QuantityPickerDialog(Context context, Item item, boolean inOrderDialog) {
        this.context = context;
        this.inOrderDialog = inOrderDialog;
        this.item = item;
        relativeLayout = new RelativeLayout(context);
        numberPicker = new NumberPicker(context);
        numberPicker.setMaxValue(50);
        numberPicker.setMinValue(1);
    }

    public void setupDialog(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        final RelativeLayout.LayoutParams numPickerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPickerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        relativeLayout.setLayoutParams(params);
        relativeLayout.addView(numberPicker, numPickerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Select the quantity");
        alertDialogBuilder.setView(relativeLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Log.e("", "New Quantity Value : " + numberPicker.getValue());
                                if (inOrderDialog) {
                                    long itemId = Auxiliary.getItemIndexInOrder(item.getId());
                                    Auxiliary.updateItemQuantity(itemId,numberPicker.getValue());
                                } else {
                                    OrderCard orderCard = new OrderCard(item, numberPicker.getValue(), 2);
                                    Auxiliary.addToOrder(orderCard);
                                    Toast.makeText(context, "Item added to order", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        alertDialog = alertDialogBuilder.create();
    }

    public void show(){
        setupDialog();
        alertDialog.show();
    }
}
