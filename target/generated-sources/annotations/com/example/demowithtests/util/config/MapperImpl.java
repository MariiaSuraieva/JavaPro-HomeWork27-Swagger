package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-21T20:10:31+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class MapperImpl implements Mapper {

    @Override
    public Employee employeeDtoToEmployee(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setName( employeeDto.name );
        employee.setCountry( employeeDto.country );
        employee.setEmail( employeeDto.email );
        employee.setAddresses( addressDtoSetToAddressSet( employeeDto.addresses ) );
        employee.setPhotos( photoDtoSetToPhotoSet( employeeDto.photos ) );

        return employee;
    }

    @Override
    public Address map(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        Address address = new Address();

        address.setCountry( addressDto.country );
        address.setCity( addressDto.city );
        address.setStreet( addressDto.street );
        address.setAddressHasActive( addressDto.addressHasActive );

        return address;
    }

    @Override
    public Photo map(PhotoDto value) {
        if ( value == null ) {
            return null;
        }

        Photo photo = new Photo();

        photo.setLinkToPhoto( value.linkToPhoto );
        photo.setX( value.x );
        photo.setY( value.y );
        photo.setCreatedDate( value.createdDate );

        return photo;
    }

    @Override
    public EmployeeDto employeeToEmployeeDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        String name = null;
        String country = null;
        String email = null;
        Set<AddressDto> addresses = null;
        Set<PhotoDto> photos = null;

        name = employee.getName();
        country = employee.getCountry();
        email = employee.getEmail();
        addresses = addressSetToAddressDtoSet( employee.getAddresses() );
        photos = photoSetToPhotoDtoSet( employee.getPhotos() );

        EmployeeDto employeeDto = new EmployeeDto( name, country, email, addresses, photos );

        return employeeDto;
    }

    @Override
    public EmployeeReadDto employeeToEmployeeReadDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeReadDto employeeReadDto = new EmployeeReadDto();

        employeeReadDto.name = employee.getName();
        employeeReadDto.email = employee.getEmail();
        employeeReadDto.country = employee.getCountry();

        return employeeReadDto;
    }

    @Override
    public Employee employeeReadDtoToEmployee(EmployeeReadDto employeeReadDto) {
        if ( employeeReadDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setName( employeeReadDto.name );
        employee.setEmail( employeeReadDto.email );
        employee.setCountry( employeeReadDto.country );

        return employee;
    }

    protected Set<Address> addressDtoSetToAddressSet(Set<AddressDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Address> set1 = new HashSet<Address>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AddressDto addressDto : set ) {
            set1.add( map( addressDto ) );
        }

        return set1;
    }

    protected Set<Photo> photoDtoSetToPhotoSet(Set<PhotoDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Photo> set1 = new HashSet<Photo>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PhotoDto photoDto : set ) {
            set1.add( map( photoDto ) );
        }

        return set1;
    }

    protected AddressDto addressToAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.addressHasActive = address.getAddressHasActive();
        addressDto.country = address.getCountry();
        addressDto.city = address.getCity();
        addressDto.street = address.getStreet();

        return addressDto;
    }

    protected Set<AddressDto> addressSetToAddressDtoSet(Set<Address> set) {
        if ( set == null ) {
            return null;
        }

        Set<AddressDto> set1 = new HashSet<AddressDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Address address : set ) {
            set1.add( addressToAddressDto( address ) );
        }

        return set1;
    }

    protected PhotoDto photoToPhotoDto(Photo photo) {
        if ( photo == null ) {
            return null;
        }

        PhotoDto photoDto = new PhotoDto();

        photoDto.linkToPhoto = photo.getLinkToPhoto();
        photoDto.x = photo.getX();
        photoDto.y = photo.getY();
        photoDto.createdDate = photo.getCreatedDate();

        return photoDto;
    }

    protected Set<PhotoDto> photoSetToPhotoDtoSet(Set<Photo> set) {
        if ( set == null ) {
            return null;
        }

        Set<PhotoDto> set1 = new HashSet<PhotoDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Photo photo : set ) {
            set1.add( photoToPhotoDto( photo ) );
        }

        return set1;
    }
}
