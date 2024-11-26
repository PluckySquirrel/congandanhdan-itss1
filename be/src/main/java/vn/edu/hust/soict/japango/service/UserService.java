package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.dto.user.AuthenticateRequestDTO;
import vn.edu.hust.soict.japango.dto.user.AuthenticateResponseDTO;

public interface UserService {
    AuthenticateResponseDTO authenticate(AuthenticateRequestDTO request);
}
