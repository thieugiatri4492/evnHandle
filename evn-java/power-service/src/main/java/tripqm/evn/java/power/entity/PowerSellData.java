package tripqm.evn.java.power.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@IdClass(PowerSellDataId.class)
@SqlResultSetMapping(
        name = "PowerSellDataMapping",
        entities = @EntityResult(
                entityClass = PowerSellData.class,
                fields = {
                        @FieldResult(name = "maDviqly", column = "MA_DVIQLY"),
                        @FieldResult(name = "maKhang", column = "MA_KHANG"),
                        @FieldResult(name = "tenKhang", column = "TEN_KHANG"),
                        @FieldResult(name = "manhomKhang", column = "MANHOM_KHANG"),
                        @FieldResult(name = "csuat", column = "CSUAT"),
                        @FieldResult(name = "thuongPham", column = "THUONG_PHAM"),
                        @FieldResult(name = "rowNum", column = "ROW_NUM")
                }
        )
)
@NamedStoredProcedureQuery(
        name = "PowerSellData.getPowerData",
        procedureName = "PKG_DUBAOTHUONGPHAMCMIS.GET_THUONGPHAM",
        resultSetMappings = {"PowerSellDataMapping"},
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_MADVIQLY", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_THANG", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NAM", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_LOAIDULIEU", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_SOKY", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_LOAIKH", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PAGE_NUMBER", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PAGE_SIZE", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_RST", type = void.class),
                //@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_RST", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_TOTAL_RECORDS", type = Integer.class)

        })
public class PowerSellData  {

    @Id
    @Column(name = "MA_DVIQLY")
    String maDviqly;

    @Id
    @Column(name = "MA_KHANG")
    String maKhang;

    @Column(name = "TEN_KHANG")
    String tenKhang;

    @Column(name = "MANHOM_KHANG")
    String maNhomKhang;

    @Column(name = "CSUAT")
    Integer congSuat;

    @Column(name = "THUONG_PHAM")
    Integer thuongPham;

    @Column(name = "ROW_NUM")
    Integer rowNum;
}
