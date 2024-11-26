package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.dto.user.AuthenticateRequestDTO;
import vn.edu.hust.soict.japango.dto.user.AuthenticateResponseDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterRequestDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterResponseDTO;

public interface UserService {
    AuthenticateResponseDTO authenticate(AuthenticateRequestDTO request);
    RegisterResponseDTO register(RegisterRequestDTO request);
}
