$(function(){

    const appendTourist = function(data){
        var touristCode = '<a href="#" class="tourist-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#tourist-list')
            .append('<div>' + touristCode + '</div>');
    };

//    Loading tourists on load page
    $.get('/tourists/', function(response)
    {
        for(i in response) {
            appendTourist(response[i]);
        }
    });

    //Show adding tourist form
    $('#show-add-tourist-form').click(function(){
        $('#registr-form').css('display', 'flex');
    });

    //Closing adding registr form
    $('#registr-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting tourist
    $(document).on('click', '.tourist-link', function(){
        var link = $(this);
        var touristId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tourists/' + touristId,
            success: function(response)
            {
                var code = '<span>Дата рождения:' + response.birthday + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Турист не найден!');
                }
            }
        });
        return false;
    });

    //Adding tourist
    $('#save-tourist').click(function()
    {
//        var data = $('#registr-form form').serialize();
        $.ajax({
            type: "POST",
            url: '/tourists/',
            dataType: html,
            data: {
                name: ${tourist.name},
                birthday: ${tourist.birthday},
                seat: ${touristId.seat}
            }
            success: function(response)
            {
                $('#registr-form').css('display', 'none');
                var tourist = {};
                tourist.id = response;
                var dataArray = $('#registr-form form').serializeArray();
                for(i in dataArray) {
                    tourist[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTourist(tourist);
            }
        });
        return false;
    });
});
