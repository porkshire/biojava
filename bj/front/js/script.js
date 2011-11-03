$(function(){
	$("#width, #height").change(function(){
		$(this)
			.parent()
			.parent()
			.find("strong")
				.text($(this).val());
	}).change();
	
	/* http://www.jsphylosvg.com/documentation.php */
	$("#form").submit(function(e){
		e.preventDefault();
		
		$("#chart").empty();
		var phylocanvas = new Smits.PhyloCanvas(
			{ newick: $("#data").val() },
			'chart', 
			$("#width").val(), 
			$("#height").val(),
			$("#type").val()
		);
	});
	
});