package projectA.projectA.model.companyWorkModel;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyWorkReq {

  private Integer id;
  private String companyName;
  private String province;
  private String district;
  private String jobTitle;
  private String salary;
  private Object welfareBenefits;
  private String detailWork;
  private Object feature;
  private String contact;
}
