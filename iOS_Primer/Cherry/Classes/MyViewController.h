#import <UIKit/UIKit.h>
#import	<AVFoundation/AVAudioPlayer.h>

@interface MyViewController : UIViewController <AVAudioPlayerDelegate> {	
	IBOutlet UISegmentedControl *colorChooser;
	AVAudioPlayer *player;
}

@property (nonatomic, retain) AVAudioPlayer	*player;
@property (nonatomic, retain) IBOutlet UISegmentedControl *colorChooser;

- (IBAction)changeBackground;
- (IBAction)play;
- (IBAction)stop;

@end
