//
//  WhiteNoiseAppDelegate.h
//  WhiteNoise
//
//  Created by James Hovell on 1/18/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class WhiteNoiseViewController;

@interface WhiteNoiseAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    WhiteNoiseViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet WhiteNoiseViewController *viewController;

@end

