chrome.extension.onMessage.addListener(function(request, sender, sendResponse) {
	chrome.tabs.update(sender.tab.id, {url: request.redirect});
	
	if (request.greetings == "caught")
		sendResponse({rebuke: "You need to better utilize your time"});
});