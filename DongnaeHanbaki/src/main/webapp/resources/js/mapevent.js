// 마커 종류 선택 변경 시 호출되는 함수
document.getElementById('markerType').addEventListener('change', function () {
    var markerType = this.value;
    var markerContentContainer = document.getElementById('markerContentContainer');

    // 마커 종류에 따라 내용 입력 필드 변경
    if (markerType === '이벤트') {
        markerContentContainer.innerHTML = `
            <select class="form-select" id="markerContent" required>
                <option value="" selected disabled hidden>내용을 선택해주세요</option>
                <option value="전국 축제 및 행사">전국 축제 및 행사</option>
                <option value="우리동네 행사">우리동네 행사</option>
                <option value="실시간 버스킹">실시간 버스킹</option>
            </select>
        `;
    } else {
        markerContentContainer.innerHTML = `
            <input type="text" class="form-control" id="markerContent" required>
        `;
    }
});