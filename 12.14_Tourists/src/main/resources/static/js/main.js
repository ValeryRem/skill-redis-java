//все var заменены на let

$(function(){

    const appendTourist = function(data){
        let touristCode = '<a href="#" class="tourist-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#tourist-list')
            .append('<div>' + touristCode + '</div>');
        $("#count").text($('#tourist-list > div').length);
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
        let link = $(this);
        let touristId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tourists/' + touristId,
            success: function(response)
            {
                $(".birthday").remove();
                let code = '<span class="birthday">Дата рождения:' + response.birthday + '</span>';
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
    $('#save-info').click(function(e){
        e.preventDefault(); // Отменяем дефолтное событие отправки формы (чтобы страница не перезагрузилась)

        let tourist = {};
        $.each($('#registr-form form').serializeArray(), function() {
            tourist[this.name] = this.value;
        });

        console.log(tourist);
        $.ajax({
            method: "POST",
            url: '/tourists/',
            data: JSON.stringify(tourist), // Преобразуем JS объект в JSON строку
            contentType: 'application/json', // Добавляем хедер Content-type который необходим, чтобы spring понял
            //что к нему пришел JSON и он выполнил метод контроллера :)
            success: function(response) {
                $('#registr-form').css('display', 'none');
                tourist.id = response;
                appendTourist(tourist);
            },
            error: function(response) {
                let json = JSON.parse(response.responseText);
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
