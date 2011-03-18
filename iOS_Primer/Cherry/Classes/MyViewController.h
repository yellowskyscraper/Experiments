#import <UIKit/UIKit.h>
#import	<AVFoundation/AVAudioPlayer.h>
#import <AVFoundation/AVAudioSession.h>

@interface MyViewController : UIViewController <AVAudioPlayerDelegate> {	
	IBOutlet UISegmentedControl *colorChooser;
	AVAudioPlayer *player;
	AVAudioSession *session;
}

@property (nonatomic, retain) AVAudioPlayer	*player;
@property (nonatomic, retain) IBOutlet UISegmentedControl *colorChooser;

- (IBAction)changeBackground;
- (IBAction)play;
- (IBAction)stop;

@end
