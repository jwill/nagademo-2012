package com.html5gamebook.core;


import java.util.*;
import static playn.core.PlayN.*;
import playn.core.*;
import org.json.*;

public class CardSpriteSheet implements AssetWatcher.Listener, ResourceCallback<String> {
  AssetWatcher assetWatcher;
  HashMap<String,Image> objects;
  JSONArray definitionList;

  String filename;
  Image image;

  public CardSpriteSheet(String filename) {
    this.filename = filename;
    this.assetWatcher = new AssetWatcher(this);
    this.image = assets().getImage(filename);
    this.assetWatcher.add(this.image);

    assets().getText(filename+".def", this);
    this.assetWatcher.start();
  }

  public void done() {
    try {
     objects = new HashMap<String, Image>();
     for (int i = 0; i<definitionList.length(); i++) {
       JSONObject obj = definitionList.getJSONObject(i);
       Image region = image.subImage(obj.getInt("x"), obj.getInt("y"), obj.getInt("width"), obj.getInt("height"));
       objects.put(obj.getString("name"), region);
     }
    } catch(JSONException ex) {}
  }

  public void done(String resource) {
    try {
      definitionList = new JSONArray(resource);
    } catch(JSONException ex) {
      ex.printStackTrace();
    }
  }


  public void error(Throwable e) {
    log().error(e.toString());
  }

  public Image get(String name) {
    return objects.get(name);
  }

}
