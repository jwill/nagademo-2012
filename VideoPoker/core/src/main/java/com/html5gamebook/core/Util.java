package com.html5gamebook.core;

import playn.core.*;
import static playn.core.PlayN.*;


class Util {
  static Layer createMessageText(String text, int fontSize, Integer fontColor) {
      Font font = graphics().createFont("Sans serif", Font.Style.PLAIN, fontSize);
      TextLayout layout = graphics().layoutText(
        text, new TextFormat().withFont(font).withWrapWidth(200));
      Layer textLayer = createTextLayer(layout, fontColor);
      return textLayer;
    }

  static Layer createTextLayer(TextLayout layout, Integer fontColor) {
      CanvasImage image = graphics().createImage((int)Math.ceil(layout.width()),
                                                 (int)Math.ceil(layout.height()));
      if (fontColor != null) image.canvas().setFillColor(fontColor);
      image.canvas().fillText(layout, 0, 0);
      return graphics().createImageLayer(image);
    }

}
