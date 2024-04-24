package ptithcm.API_QLDSV_TC.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ptithcm.API_QLDSV_TC.DTO.TaiKhoanDTO;
import ptithcm.API_QLDSV_TC.Model.TaiKhoan;
import ptithcm.API_QLDSV_TC.Repository.SinhVienRepository;
import ptithcm.API_QLDSV_TC.Repository.TaiKhoanRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaiKhoanService {
    @Autowired
    TaiKhoanRepository taiKhoanRepository;
    @Autowired
    QuyenService quyenService;

    @Autowired
    SinhVienRepository sinhVienRepository;

    @Autowired
    LopService lopService;
    public TaiKhoanDTO getInfo (String username) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findById(username);
        TaiKhoan taiKhoan = optionalTaiKhoan.get();
        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
        taiKhoanDTO.setUsername(username);
        if(taiKhoan.getQuyen().getTenQuyen().equals("SINHVIEN")) {

            taiKhoanDTO.setHo(taiKhoan.getSinhVien().getHo());
            taiKhoanDTO.setTen(taiKhoan.getSinhVien().getTen());
            taiKhoanDTO.setTenQuyen(taiKhoan.getQuyen().getTenQuyen());
        } else {
            taiKhoanDTO.setHo(taiKhoan.getGiangVien().getHo());
            taiKhoanDTO.setTen(taiKhoan.getGiangVien().getTen());
            taiKhoanDTO.setTenQuyen(taiKhoan.getQuyen().getTenQuyen());
        }
        return taiKhoanDTO;
    }

    public boolean saveNewLogin(TaiKhoanDTO taiKhoanDTO) {
        try {
            if(taiKhoanDTO.getIdQuyen() == 1){
                taiKhoanRepository.taoTaiKhoanSinhVien(
                        taiKhoanDTO.getUsername(),
                        taiKhoanDTO.getPassword(),
                        taiKhoanDTO.getIdQuyen(),
                        taiKhoanDTO.getHo(),
                        taiKhoanDTO.getTen(),
                        taiKhoanDTO.getPhai(),
                        taiKhoanDTO.getMa(),
                        false,
                        taiKhoanDTO.getSdt(),
                        taiKhoanDTO.getEmail()
                );
            } else {
                taiKhoanRepository.taoTaiKhoanGiangVien(
                        taiKhoanDTO.getUsername(),
                        taiKhoanDTO.getPassword(),
                        taiKhoanDTO.getIdQuyen(),
                        taiKhoanDTO.getHo(),
                        taiKhoanDTO.getTen(),
                        taiKhoanDTO.getMa(),
                        taiKhoanDTO.getSdt(),
                        taiKhoanDTO.getEmail()
                );
            }
        } catch (DataAccessException dataAccessException) {
            System.out.println(dataAccessException.getMessage());
            return false;
        }
        return true;
    }
    public boolean checkUsername(String username) {
        if(taiKhoanRepository.findById(username).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Map<String,Object>> findDanhSachTaiKhoan() {
        return taiKhoanRepository.findDanhSachTK();
    }
}