var d = new Date();
var curr_day = d.getDay();
var curr_hours = d.getHours();

var urlArray = 
[
"http://www.huffingtonpost.com/detroit/",
"http://www.creativeapplications.net/",
"http://fffff.at/",
"http://www.ted.com/talks",
"http://www.youtube.com/watch?v=8i47-QBL4Qo",
"http://www.npr.org/series/tiny-desk-concerts/",
"http://www.youtube.com/watch?v=hgX220ZZ3rA&feature=related",
"http://www.scientificamerican.com/section.cfm?id=news",
"http://www.bfi.org.uk/news/50-greatest-films-all-time",
"http://www.bfi.org.uk/news/sight-sound-2012-directors-top-ten",
"http://www.youtube.com/watch?v=ABm7DuBwJd8&feature=related",
"http://artsbeat.blogs.nytimes.com/category/books/",
"http://artsbeat.blogs.nytimes.com/category/theater/",
"http://artsbeat.blogs.nytimes.com/category/art-design/",
"http://artsbeat.blogs.nytimes.com/category/music/",
"http://www.youtube.com/watch?v=-0PrTkE5jG4&feature=player_embedded",
"http://www.sciencemag.org/",
"http://oyc.yale.edu/courses",
"http://oyc.yale.edu/philosophy/phil-176#sessions"
];

var url = urlArray[Math.floor(Math.random()*urlArray.length)];

if(curr_hours > 9 && curr_hours < 17 && curr_day > 0 && curr_day < 7) {
	chrome.extension.sendRequest({redirect: url});
} else {
	
}