function moimModalFunction(){
    var createMoimModal = new bootstrap.Modal($('#createMoimModal')[0]);
    $('#nav__create-moim').on('click', function() {
        createMoimModal.show();
    });
    
    // 모달창이 닫혔을대 폼을 리셋한다.
    $('#createMoimModal').on('hidden.bs.modal', function () {
        $(this).find('form')[0].reset();
    });
};

function submitCreateMoimForm() {
    function validateTitle(title) {
      const titleRegex = /^[a-zA-Z0-9가-힣\s]{3,}$/; // 3글자 이상, 특수문자 제외
      return titleRegex.test(title);
    }

    function validateIntroduce(introduce) {
      return introduce.length >= 10; // 10글자 이상
    }
    
    const title = $('#createMoimModal-title').val();
    const introduce = $('#createMoimModal-introduce').val();

    if (!validateTitle(title)) {
    	showDangerAlert('이름을 다시 지어주세요!', '모임 이름은 3글자 이상이어야 하며, 특수문자는 포함할 수 없습니다.', '수정을 하고 생성해주세요.');
        return;
    }

    if (!validateIntroduce(introduce)) {
        alert('모임 소개는 10글자 이상이어야 합니다.');
        showDangerAlert('소개글을 다시 써주세요!', '모임 소개는 10글자 이상이어야 합니다.', '수정을 하고 생성해주세요.');
        return;
    }

    $('#createMoimForm').submit(); // 폼 제출
}