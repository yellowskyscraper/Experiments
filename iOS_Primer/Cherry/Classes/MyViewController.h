//
//  MyViewController.h
//  Cherry
//
//  Created by James Hovell on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MyViewController : UIViewController {
	
	UISwitch *whiteSwitch;

}

@property (nonatomic, retain) IBOutlet UISwitch *whiteSwitch;

- (IBAction)changeBackground:(id)sender;

@end
