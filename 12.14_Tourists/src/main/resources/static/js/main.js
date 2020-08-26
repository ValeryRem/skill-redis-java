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
    $('#show-add-registr-form').click(function(){
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
                $(".birthday").remove();
                var code = '<span class="birthday">Дата рождения:' + response.birthday + '</span>';
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
    $('#save-info').click(function(){
        var tourist = {};
        $.each($('#registr-form form').serializeArray(), function() {
            tourist[this.name] = this.value;
        });

        console.log(tourist);
        $.ajax({
            method: "POST",
            url: '/tourists/',
            data: tourist,
            success: function(response) {
                $('#registr-form').css('display', 'none');
                tourist.id = response;
                appendTourist(tourist);
            },
            error: function(response) {
                var json = JSON.parse(response.responseText);
                if (json && json.error) {
                    alert(json.error);
                } else {
                    alert(response.responseText);
                }
            }
        });
        return false;
    });
});
