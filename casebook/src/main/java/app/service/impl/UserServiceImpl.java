package app.service.impl;

import app.domain.entities.User;
import app.domain.models.service.UserServiceModel;
import app.repository.UserRepository;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserServiceModel userServiceModel) {
        this.userRepository.save(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public UserServiceModel getById(String id) {
        return this.modelMapper.map(this.userRepository.findById(id), UserServiceModel.class);
    }

    @Override
    public void addFriend(UserServiceModel userServiceModel) {
        this.userRepository.update(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public UserServiceModel getByUsernameAndPassword(String username, String password) {

        if (this.userRepository.findByUsernameAndPassword(username, password) == null) {
            return null;
        }

        return this.modelMapper.map(this.userRepository.findByUsernameAndPassword(username, password), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> getAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(m -> this.modelMapper.map(m, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(String userId, String friendId) {
        User user1 = this.userRepository.findById(userId);
        User user2 = this.userRepository.findById(friendId);

        user1.getFriends().remove(user2);
        user2.getFriends().remove(user1);

        this.userRepository.update(user1);
        this.userRepository.update(user2);
    }
}