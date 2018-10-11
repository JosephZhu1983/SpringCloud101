package me.josephzhu.springcloud101.userservice.server;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import me.josephzhu.springcloud101.userservice.api.User;
import me.josephzhu.springcloud101.userservice.api.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserServiceController implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RedissonClient redissonClient;

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).map(userEntity ->
                User.builder()
                        .id(userEntity.getId())
                        .availableBalance(userEntity.getAvailableBalance())
                        .frozenBalance(userEntity.getFrozenBalance())
                        .name(userEntity.getName())
                        .createdAt(userEntity.getCreatedAt())
                        .build())
                .orElse(null);
    }

    @Override
    public BigDecimal consumeMoney(long investorId, BigDecimal amount) {
        RLock lock = redissonClient.getLock("User" + investorId);
        lock.lock();
        try {
            UserEntity user = userRepository.findById(investorId).orElse(null);
            if (user != null && user.getAvailableBalance().compareTo(amount)>=0) {
                user.setAvailableBalance(user.getAvailableBalance().subtract(amount));
                user.setFrozenBalance(user.getFrozenBalance().add(amount));
                userRepository.save(user);
                return amount;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal lendpayMoney(long investorId, long borrowerId, BigDecimal amount) throws Exception {
        RLock lock = redissonClient.getLock("User" + investorId);
        lock.lock();
        try {
            UserEntity investor = userRepository.findById(investorId).orElse(null);
            UserEntity borrower = userRepository.findById(borrowerId).orElse(null);

            if (investor != null && borrower != null && investor.getFrozenBalance().compareTo(amount) >= 0) {
                investor.setFrozenBalance(investor.getFrozenBalance().subtract(amount));
                userRepository.save(investor);
                borrower.setAvailableBalance(borrower.getAvailableBalance().add(amount));
                userRepository.save(borrower);
                return amount;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

}
