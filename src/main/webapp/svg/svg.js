
$(function() {

	console.info("SVG View");
	
	inDesign.getWebSocket().onmessage = function(event) {
		
		console.info("GO WS message");
		var msg = JSON.parse("{"+event.data+"}");
		var reloadResponse = msg.Envelope.Body.PartReloadResponse
		if (reloadResponse) {
			
			
			var targetId = reloadResponse[0]._a_id;
			var html = $.parseHTML(decodeHTML(reloadResponse[0].Content))
			
			console.info("Reload part: "+targetId);
			console.info("Content: "+html)
			// Get Target id from page 
			//var targetHTML = $("#"+targetId)
			
			// Replace
			setPartContent(targetId,html);
		}
		
		
	}
	console.info("Done with cnonection");
	
})

