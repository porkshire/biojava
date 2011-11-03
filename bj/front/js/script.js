$(function(){
	$("#width, #height").change(function(){
		$(this)
			.parent()
			.parent()
			.find("strong")
				.text($(this).val());
	}).change();
	
	/* http://www.jsphylosvg.com/documentation.php */
    $('#chart').addClass('loading');
    $.getJSON(
        '/json/dane.json', {
        }, function(response) {
            new Smits.PhyloCanvas({ 
                    newick: response.data 
                },
                'chart', 
                response.width, 
                response.height,
                response.type
            );
            $('#width').val(response.width);
            $('#width').change();
            $('#height').val(response.height);
            $('#height').change();
            $('#chart').removeClass('loading');
            $('#type').find('option[value="'+response.type+'"]').attr('selected', true);
    });
	
    
});
