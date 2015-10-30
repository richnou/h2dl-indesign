$(function() {
	
	
	console.info("Starting Indesign")
	
	// Open Websocket
	//----------------------
	//var connection = new WebSocket('ws://localhost:8889/indesign/websocket', ['soap']);
	
})


var inDesign = new function() {
	
	var wsConnection = null;

	this.getWebSocket = function() {
		
		console.info("Getting conection: "+wsConnection);
		if (wsConnection==null) {
			wsConnection= new WebSocket('ws://localhost:8889/indesign/websocket', ['soap']);
		}
		
		return wsConnection;
	};
	
	
}
