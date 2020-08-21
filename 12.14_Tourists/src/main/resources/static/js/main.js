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
    $('#show-add-tourist-registr-form').click(function(){
        $('#tourist-registr-form').css('display', 'flex');
    });

    //Closing adding registr form
    $('#tourist-registr-form').click(function(event){
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
    $('#save-tourist').click(function(){
        var data = $('#tourist-registr-form form').serialize();
//        var tourist = {
//                           name: ${data.name},
//                           birthday: ${data.birthday},
//                           seat: ${data.seat}
//                        };
        $.ajax({
            method: "POST",
            url: '/tourists/',
            data: html,
            data: data,
            success: function(response)
            {
                $('#tourist-registr-form').css('display', 'none');
//                var tourist = {
//                   name: ${data.name},
//                   birthday: ${data.birthday},
//                   seat: ${data.seat}
//                };
                tourist.id = response;
//                var dataArray = $('#tourist-registr-form form').serializeArray();
//                for(i in dataArray) {
//                    tourist[dataArray[i]['name']] = dataArray[i]['value'];
//                }
                appendTourist(tourist);
            }
        });
        return false;
    });
});
