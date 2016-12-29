package by.gomelagro.incoming.status;

public enum Status {
	COMPLETED,
	COMPLETED_SIGNED,
	ON_AGREEMENT,
	CANCELLED,
	ON_AGREEMENT_CANCEL,
	IN_PROGRESS,
	NOT_FOUND,
	ERROR;
	
	public String getRuValue(){
		String value = "";
		switch(this){
			case COMPLETED: value = "Выставлен"; break;
			case COMPLETED_SIGNED: value = "Выставлен. Подписан получателем"; break;
			case ON_AGREEMENT: value = "На согласовании"; break;
			case CANCELLED: value = "Аннулирован"; break;
			case ON_AGREEMENT_CANCEL: value = "Выставлен. Аннулирован поставщиком"; break;
			case IN_PROGRESS: value = "ЭСЧФ находится в обработке. Запросите статус повторно через 3 часа"; break;
			case NOT_FOUND: value = "ЭСЧФ нет в базе или нет права для просмотра статуса/выгрузки документа"; break;
			case ERROR: value = "Ошибка при выставлении ЭСЧФ на портал"; break;
			default: value = null; break;
		}
		return value;
	}
	
	public String getEnValue(){
		String value = "";
		switch(this){
			case COMPLETED: value = "COMPLETED"; break;
			case COMPLETED_SIGNED: value = "COMPLETED_SIGNED"; break;
			case ON_AGREEMENT: value = "ON_AGREEMENT"; break;
			case CANCELLED: value = "CANCELLED"; break;
			case ON_AGREEMENT_CANCEL: value = "ON_AGREEMENT_CANCEL"; break;
			case IN_PROGRESS: value = "IN_PROGRESS"; break;
			case NOT_FOUND: value = "NOT_FOUND"; break;
			case ERROR: value = "ERROR"; break;
			default: value = null; break;
		}
		return value;
	}
}

