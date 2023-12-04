package org.yangxin.service;


import org.yangxin.entity.bo.HeadLine;
import org.yangxin.entity.dto.Result;

public interface HeadLineService {
	Result<Boolean> addHeadLine(HeadLine headLine);
}