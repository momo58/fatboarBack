package com.pfa.fatboar.FatboarBack.common;

public enum GainInfo {
	
	ENTREE_DESSERT_AU_CHOIX(60, 1L), BURGER_AU_CHOIX(20, 2L), MENU_DU_JOUR(10, 3L), MENU_AU_CHOIX(6, 4L), SOIXANTE_DIX_POURCENT_REDUCTION(4, 5L);
	
	private GainInfo(Integer probabilite, Long gainId) {
		this.probabilite = probabilite;
		this.gainId = gainId;
	}
	
	private final int probabilite;
	private final Long gainId;
	
	public int getProbabilite() {
        return this.probabilite;
    }
	
	public Long getGainId() {
		return this.gainId;
	}

}
