package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.dto.user.*;

public interface UserService {
    AuthenticateResponseDTO authenticate(AuthenticateRequestDTO request);
    RegisterResponseDTO register(RegisterRequestDTO request);
    GetProfileResponseDTO getProfile(String uuid);
    UpdateProfileResponseDTO updateProfile(String uuid, UpdateProfileRequestDTO request);
    ChangePasswordResponseDTO changePassword(String uuid, ChangePasswordRequestDTO request);
    ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO request);
}
