package com.wwqk.constants;

import com.wwqk.utils.IEnum;


/**
 * 伤病类型枚举
 */
public enum InjuryTypeEnum implements IEnum{
	GS("Groin Strain", "腹股沟拉伤"), 
	H("Hamstring", "腿筋拉伤"),
	TMS("Thigh Muscle Strain", "大腿肌肉拉伤"),
	KI("Knee Injury", "膝盖受伤"),
	CMS("Calf Muscle Strain", "小腿肌肉拉伤"), 
	BI("Back Injury", "背部受伤"), 
	ILL("Illness", "疾病"), 
	CO("Concussion", "脑震荡"), 
	SA("Sprained Ankle", "踝关节扭伤"), 
	VI("Virus", "流感"), 
	VF("Vertebral Fracture", "椎骨骨折"), 
	AFI("Ankle/Foot Injury", "脚踝/脚受伤"), 
	UN("Unknown", "未知伤病"), 
	LCL("LCL Knee Ligament Injury", "膝盖外侧副韧带拉伤"), 
	CKI("Cartilage Knee Injury", "膝盖软骨骨折"), 
	BN("Broken Nose", "鼻骨骨折"),
	SI("Shoulder Injury", "肩膀受伤"),
	CSI("Calf/Shin Injury", "小腿/胫骨损伤"),
	RC("Renal Colic", "肾绞痛");
	
	/** 键 */
	private String key;

	/** 整型键值 */
	private Integer integerKey;

	/** 值 */
	private String value;
	
	private InjuryTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;	
		this.integerKey = Integer.valueOf(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getIntegerKey() {
		return integerKey;
	}

	public void setIntegerKey(Integer integerKey) {
		this.integerKey = integerKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getName() {
		return this.name();
	}
}
