package com.msincuba.play.mapper;

import com.msincuba.play.domain.Banking;
import com.msincuba.play.domain.Benefit;
import com.msincuba.play.domain.Contact;
import com.msincuba.play.domain.Employment;
import com.msincuba.play.domain.Member;
import com.msincuba.play.domain.Plan;
import com.msincuba.play.dto.BankingDto;
import com.msincuba.play.dto.BenefitDto;
import com.msincuba.play.dto.ContactDto;
import com.msincuba.play.dto.EmploymentDto;
import com.msincuba.play.dto.MemberDto;
import com.msincuba.play.dto.PlanDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayMapper {

    Member convert(MemberDto dto);    
    MemberDto convert(Member entity);
    Banking convert(BankingDto dto);    
    BankingDto convert(Banking entity);
    Benefit convert(BenefitDto dto);    
    BenefitDto convert(Benefit entity);
    Contact convert(ContactDto dto);    
    ContactDto convert(Contact entity);
    Employment convert(EmploymentDto dto);    
    EmploymentDto convert(Employment entity);
    Plan convert(PlanDto dto);    
    PlanDto convert(Plan entity);
}
