//
//  CherryAppDelegate.h
//  Cherry
//
//  Created by James Hovell on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MyViewController;

@interface CherryAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
	MyViewController *myViewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) MyViewController *myViewController;

@end

