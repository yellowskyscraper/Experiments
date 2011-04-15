//
//  NoiseAppDelegate.h
//  Noise
//
//  Created by James Hovell on 3/18/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class NoiseViewController;

@interface NoiseAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    NoiseViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet NoiseViewController *viewController;

@end

