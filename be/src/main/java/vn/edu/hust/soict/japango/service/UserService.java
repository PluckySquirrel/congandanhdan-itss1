package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.dto.user.*;

public interface UserService {
    AuthenticateResponseDTO authenticate(AuthenticateRequestDTO request);
    RegisterResponseDTO register(RegisterRequestDTO request);
    UpdateProfileResponseDTO updateProfile(String uuid, UpdateProfileRequestDTO request);
}
