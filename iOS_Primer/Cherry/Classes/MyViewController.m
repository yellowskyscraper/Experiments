#import "MyViewController.h"


@implementation MyViewController;
@synthesize colorChooser, player;


- (IBAction)changeBackground {
	if(colorChooser.selectedSegmentIndex == 0){
		self.view.backgroundColor = [UIColor darkGrayColor];
		[self stop];
	} else if(colorChooser.selectedSegmentIndex == 1){
		self.view.backgroundColor = [UIColor brownColor];
		[self play];
	}
}


// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
/*
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization.
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	self.view.backgroundColor = [UIColor darkGrayColor];
	NSString *path = [[NSBundle mainBundle] pathForResource:@"Noise_Brown" ofType:@"caf"];
	NSURL *file = [[NSURL alloc] initFileURLWithPath:path];
	
	AVAudioPlayer *p = [[AVAudioPlayer alloc] initWithContentsOfURL:file error:nil];
	p.numberOfLoops = -1;
	[file release];
	
	self.player = p;
	[p release];
	
	[player prepareToPlay];
	[player setDelegate:self];
	
    [super viewDidLoad];
}

- (IBAction)play {
	[self.player play];
}

- (IBAction)stop {
	[self.player stop];
}

- (void) audioPlayerDidFinishPlaying: (AVAudioPlayer *) player successfully: (BOOL) completed {
	//[self.player play];
}


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)dealloc {
	[colorChooser release]; 
    [super dealloc];
}

@end
