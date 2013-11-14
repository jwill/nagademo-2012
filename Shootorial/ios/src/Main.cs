using System;
using MonoTouch.Foundation;
using MonoTouch.UIKit;

using playn.ios;
using playn.core;
using com.html5gamebook.core;

namespace com.html5gamebook
{
  [Register ("AppDelegate")]
  public partial class AppDelegate : IOSApplicationDelegate {
    public override bool FinishedLaunching (UIApplication app, NSDictionary options) {
      app.SetStatusBarHidden(true, true);
      var pconfig = new IOSPlatform.Config();
      // use pconfig to customize iOS platform, if needed
      IOSPlatform.register(app, pconfig);
      PlayN.run(new Shootorial());
      return true;
    }
  }

  public class Application {
    static void Main (string[] args) {
      UIApplication.Main (args, null, "AppDelegate");
    }
  }
}
