$(function(){

    const appendTourist = function(data){
        var touristCode = '<a href="#" class="tourist-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#tourist-list')
            .append('<div>' + touristCode + '</div>');
    };

    //Loading books on load page
//    $.get('/books/', function(response)
//    {
//        for(i in response) {
//            appendBook(response[i]);
//        }
//    });

    //Show adding book form
    $('#show-add-tourist-form').click(function(){
        $('#registr-form').css('display', 'flex');
    });

    //Closing adding registr. form
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

    //Adding book
    $('#save-tourist').click(function()
    {
        var data = $('#registr-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tourists/',
            data: data,
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
