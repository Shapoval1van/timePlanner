$('#priority').on('change',function(){
    var val1 = $(this).find(':selected').text();
    switch (val1){
        case 'LOW':
            $(this).removeClass('btn-info btn-warning btn-danger');
            break;
        case 'MEDIUM':
            $(this).removeClass('btn-info btn-warning btn-danger').addClass('btn-info');
            break;
        case 'HIGH':
            $(this).removeClass('btn-info btn-warning btn-danger').addClass('btn-warning');
            break;
        case 'CRITICAL':
            $(this).removeClass('btn-info btn-warning btn-danger').addClass('btn-danger');
            break;
        default:
            break;
    }
});

if ($('#sprint-selector option').length != 0) {
    $('.alert').css({"display": "none"});
    $('#submit').css({"display": "block"});
} else {
    $('.alert').css({"display": "block"});
    $('#submit').css({"display": "none"});
}

$(document).ready(function() {
    $(".task-selector").select2();
    var date = $(this).find(':selected').data('date');
});

$('#sprint-selector').on('change',function () {
    var val1 = $(this).find(':selected').val();
    var date = $(this).find(':selected').data('date');
    var url = '/task-for-sprint-'+val1+'id';
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            $('#task-selector').find('option').remove();
            $('.select2-selection__rendered li').remove();
            data.forEach(function(item, i){
                var name = item.name;
                $('#task-selector').append($('<option></option>').val(item.id).html(name))
            });
            $('#planedFinishDate').attr('max', date);
        }
    });
})